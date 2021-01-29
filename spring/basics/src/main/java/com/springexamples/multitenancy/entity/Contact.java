package com.springexamples.multitenancy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    private Long id;

    private Timestamp tmsCreate;
    private Timestamp tmsUpdate;
    private String agntCreate;
    private String agntUpdate;
    private String cdEVENT;
    private String cdProfile;
    private String cdClient;
    private String cdSession;
    @ManyToOne
    @JoinColumn(name="CD_TP_CHANNEL")
    private Integer cdTpBon;
    private String cdAgent;
    private Timestamp eventDate;
    private String result;
}
