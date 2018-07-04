package com.example.demo5.process;

import com.example.demo5.map.Habitat;

public interface Processor<T extends Habitat> {
    void process(final T t);
}
