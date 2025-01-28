package com.aarevalo.holidays.domain.usecases

import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.State
import com.aarevalo.holidays.domain.repository.RemoteRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: RemoteRepository,
) {

    suspend operator fun invoke(): Pair<Country, State> {
        return repository.getCurrentLocation()
    }
}
