package io.zayasanton.app.api

import io.zayasanton.app.api.models.request.CreateDoctorRequest
import io.zayasanton.app.api.models.request.GetDoctorByIDRequest
import io.zayasanton.app.api.models.request.GetDoctorsRequest
import io.zayasanton.app.api.models.request.UpdateDoctorRequest
import io.zayasanton.app.api.models.request.RemoveDoctorRequest
import io.zayasanton.app.api.models.request.retrieveSessionId
import io.zayasanton.app.api.models.response.CreateDoctorResponse
import io.zayasanton.app.api.models.response.GetDoctorByIDResponse
import io.zayasanton.app.api.models.response.GetDoctorsResponse
import io.zayasanton.app.api.models.response.UpdateDoctorResponse
import io.zayasanton.app.types.Constants.SESSION_ID_KEY
import io.zayasanton.app.types.Constants.USER_ID_KEY
import io.zayasanton.app.types.DoctorContactAPIException
import io.zayasanton.app.util.WebClientFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class DoctorContactApi(
    webClientFactory: WebClientFactory
) {
    val webClient: WebClient = webClientFactory.getClient("doctorContactApi")

    suspend fun getDoctors(request: GetDoctorsRequest): Flux<GetDoctorsResponse> {
        return webClient.get()
            .uri {
                it.path("/v1/doctors/{userId}")
                it.build(request.userId)
            }
            .headers {
                it.set(SESSION_ID_KEY, request.retrieveSessionId())
                it.set(USER_ID_KEY, request.userId)
            }
            .retrieve()
            .bodyToFlux(GetDoctorsResponse::class.java)
    }

    suspend fun getDoctorByID(request: GetDoctorByIDRequest): Flux<GetDoctorByIDResponse> {
        return webClient.get()
            .uri {
                it.path("/v1/doctors/get/{doctorId}")
                it.build(request.doctorId)
            }
            .headers {
                it.set(SESSION_ID_KEY, request.retrieveSessionId())
                it.set(USER_ID_KEY, request.userId)
            }
            .retrieve()
            .bodyToFlux(GetDoctorByIDResponse::class.java)
    }

    suspend fun createDoctor(request: CreateDoctorRequest): Flux<CreateDoctorResponse> {
        return webClient.post()
            .uri("/v1/doctors/add")
            .headers {
                it.set(SESSION_ID_KEY, request.retrieveSessionId())
                it.set(USER_ID_KEY, request.userId)
            }
            .body(
                BodyInserters.fromValue(
                    request.data
                )
            )
            .exchangeToFlux {
                when (it.statusCode()) {
                    HttpStatus.CREATED -> it.bodyToFlux(CreateDoctorResponse::class.java)
                    else -> throw DoctorContactAPIException("Error in API call, received ${it.statusCode()}")
                }
            }
    }

    suspend fun updateDoctor(request: UpdateDoctorRequest): Flux<UpdateDoctorResponse> {
        return webClient.put()
            .uri("/v1/doctors/edit")
            .headers {
                it.set(SESSION_ID_KEY, request.retrieveSessionId())
                it.set(USER_ID_KEY, request.userId)
            }
            .body(
                BodyInserters.fromValue(
                    request.data
                )
            )
            .exchangeToFlux {
                when (it.statusCode()) {
                    HttpStatus.OK -> it.bodyToFlux(UpdateDoctorResponse::class.java)
                    else -> throw DoctorContactAPIException("Error in API call, received ${it.statusCode()}")
                }
            }
    }

    suspend fun removeDoctor(request: RemoveDoctorRequest): Flux<UpdateDoctorResponse> {
        val (doctorId) = request.data

        return webClient.delete()
            .uri {
                it.path("/v1/doctors/delete/{doctorId}")
                it.build(doctorId)
            }
            .headers {
                it.set(SESSION_ID_KEY, request.retrieveSessionId())
                it.set(USER_ID_KEY, request.userId)
            }
            .exchangeToFlux {
                when (it.statusCode()) {
                    HttpStatus.OK -> it.bodyToFlux(UpdateDoctorResponse::class.java)
                    else -> throw DoctorContactAPIException("Error in API call, received ${it.statusCode()}")
                }
            }
    }
}