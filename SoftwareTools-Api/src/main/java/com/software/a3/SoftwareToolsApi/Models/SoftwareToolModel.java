package com.software.a3.SoftwareToolsApi.Models;

import com.software.a3.SoftwareToolsApi.Models.Enum.ESoftwareTypeTool;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "app_softwareTool")
public class SoftwareToolModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description",nullable = false, length = 100)
    private String description;
    @Column(name = "typetool", nullable = false, length = 50)
    private ESoftwareTypeTool typeTool;
    @Column(name = "toolWebsite",nullable = false, length = 100)
    private String toolWebsite;

}
