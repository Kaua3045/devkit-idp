package com.kaua.devkit.platform.application;

public abstract class UseCase<I, O> {

    public abstract O execute(I input);
}
