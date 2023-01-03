package kr.hs.entrydsm.exit.domain.major.persistence

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "tbl_major")
@Entity
class Major(
     val name: String
): BaseUUIDEntity()