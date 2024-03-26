package br.com.xpe.model

import br.com.xpe.entity.HotelEntity
import org.bson.types.ObjectId

data class HotelDTO(
    val id: String,
    val name: String,
    val avgPrice: Double,
    val status: Boolean
) {
    fun toEntity(): HotelEntity {
        return HotelEntity(
            id = ObjectId.get(),
            name = this.name,
            avgPrice = this.avgPrice,
            status = this.status
        )
    }
}