package fr.uca.progfonc.td1;

public record Lst<T>(T car, Lst<T> cdr) { }
