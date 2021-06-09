package com.peteralexbizjak.europaopen.db.repositories.implementations

import com.peteralexbizjak.europaopen.db.daos.CountryDao
import com.peteralexbizjak.europaopen.db.entities.CountryEntity
import com.peteralexbizjak.europaopen.db.repositories.ICountryDBRepository
import com.peteralexbizjak.europaopen.utils.exceptions.DatabaseException

internal class CountryDBRepository(
    private val countryDAO: CountryDao
) : ICountryDBRepository {
    @Throws(DatabaseException::class)
    override suspend fun storeCountries(vararg countries: CountryEntity): Boolean {
        if (countries.isEmpty()) {
            throw DatabaseException("You need to provide at least one CountryEntity")
        }
        val hasProperDirection = countries.all {
            it.appearsInDirection == DIRECTION_TO ||
                    it.appearsInDirection == DIRECTION_FROM ||
                    it.appearsInDirection == DIRECTION_BOTH
        }
        if (hasProperDirection) {
            countryDAO.storeCountries(*countries)
            return true
        }
        return false
    }

    @Throws(DatabaseException::class)
    override suspend fun retrieveCountries(): List<CountryEntity> {
        val countries = countryDAO.retrieveAllCountries()
        if (countries.isEmpty()) {
            throw DatabaseException("No records in the database")
        }
        return countries
    }

    @Throws(DatabaseException::class)
    override suspend fun retrieveCountries(direction: String): List<CountryEntity> {
        if (direction != DIRECTION_TO && direction != DIRECTION_FROM && direction != DIRECTION_BOTH) {
            throw DatabaseException("Invalid direction $direction, should be 'to', 'from' or 'both'")
        }
        return if (direction == DIRECTION_TO || direction == DIRECTION_FROM) {
            countryDAO
                .retrieveAllCountries()
                .filter {
                    if (direction == DIRECTION_TO) it.appearsInDirection == "to" || it.appearsInDirection == "both"
                    else it.appearsInDirection == "from" || it.appearsInDirection == "both"
                }
        } else {
            countryDAO.retrieveCountriesByDirection(direction)
        }
    }

    override suspend fun isTableEmpty(): Boolean = countryDAO.retrieveTableSize() == 0

    override suspend fun clearTable() = countryDAO.clearTable()

    companion object {
        private const val DIRECTION_TO = "to"
        private const val DIRECTION_FROM = "from"
        private const val DIRECTION_BOTH = "both"
    }
}