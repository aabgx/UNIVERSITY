package com.example.tema_bun.domain;

import java.util.Objects;
import java.util.UUID;

public interface Entitate<ID> {
    void setId(ID id);
    ID getId();
}
