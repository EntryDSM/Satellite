package kr.hs.entrydsm.satellite.domain.major.dto

import java.util.UUID

data class MajorElement(
    val id: UUID,
    val name: String
) {
    constructor(majorJpaEntity: kr.hs.entrydsm.satellite.domain.major.persistence.MajorJpaEntity) : this(
        id = majorJpaEntity.id,
        name = majorJpaEntity.name
    )
}