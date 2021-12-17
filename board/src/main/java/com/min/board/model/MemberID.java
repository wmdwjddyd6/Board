package com.min.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberID implements Serializable {

    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;
}
