package br.com.xpe.repository

import br.com.xpe.entity.HotelEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class HotelRepository : PanacheMongoRepository<HotelEntity>