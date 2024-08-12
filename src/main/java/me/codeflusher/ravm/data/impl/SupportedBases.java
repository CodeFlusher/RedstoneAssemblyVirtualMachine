package me.codeflusher.ravm.data.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.codeflusher.ravm.data.IBase;

@AllArgsConstructor
public enum SupportedBases implements IBase {
    BINARY(2, 'b'),
    OCTAGONAL(8, 'o'),
    DECIMAL(10, 'd'),
    HEXAGONAL(16, 'x');

    @Getter
    private final int base;
    @Getter
    private final char prefix;
}
