package com.andersen.domain.entities.filters

data class CharacterFilter (
    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null)

data class EpisodeFilter (
    var name: String? = null,
    var episode: String? = null)

data class LocationFilter (
    var name: String? = null,
    var type: String? = null,
    var dimension: String? = null)
