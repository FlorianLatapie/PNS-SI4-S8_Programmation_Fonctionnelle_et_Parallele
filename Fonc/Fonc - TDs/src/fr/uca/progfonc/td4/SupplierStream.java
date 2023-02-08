package fr.uca.progfonc.td4;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;

public class SupplierStream<T> implements BasicStream<T> {
    Supplier<Optional<T>> optionalSupplier;

    public SupplierStream(Supplier<Optional<T>> supplier) {
        this.optionalSupplier = supplier;
    }

    @Override
    public void forEach(Consumer<T> action) {
        var supplier = optionalSupplier.get();
        if (supplier.isEmpty()) {
            return;
        }
        action.accept(supplier.get());
        forEach(action);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        var supplier = optionalSupplier.get();
        if (supplier.isEmpty()) {
            return Optional.empty();
        }
        var atomicSupplier = new AtomicReference<>(supplier.get());
        forEach(x -> atomicSupplier.set(accumulator.apply(x, atomicSupplier.get())));
        return Optional.of(atomicSupplier.get());
    }

    @Override
    public BasicStream<T> filter(Predicate<T> predicate) {
        return new SupplierStream<>(
                () -> {
                    for (var data = optionalSupplier.get(); data.isPresent(); data = optionalSupplier.get()) {
                        if (predicate.test(data.get())) {
                            return data;
                        }
                    }

                    return Optional.empty();
                }
        );
    }

    @Override
    public BasicStream<T> limit(long maxSize) {
        var i = new AtomicLong(0);
        return new SupplierStream<>(() -> {
                var supplier = optionalSupplier.get();
                if (i.getAndIncrement() < maxSize && supplier.isPresent()){
                    return supplier;
                }
                return Optional.empty();
            }
        );
    }

    @Override
    public <R> BasicStream<R> map(Function<T, R> mapper) {
        return new SupplierStream<>(
                () -> optionalSupplier.get().map(mapper)
        );
    }
}
