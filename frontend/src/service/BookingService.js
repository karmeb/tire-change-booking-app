import axios from "axios";

const API_BASE_URL = 'http://localhost:8080/api'

function getAvailableTimes(dateFrom, dateTo, workshopNames = [], vehicleTypes= [] ) {
    return axios.get(`${API_BASE_URL}/booking/available-times`, {
        params: {
            from: dateFrom,
            to: dateTo,
            workshops: workshopNames.length? workshopNames : undefined,
            vehicleTypes: vehicleTypes.length? vehicleTypes : undefined,
        },
        paramsSerializer:  {
            indexes: null
        }
    })
}

function bookTime(timeSlotId, contactInfo, workshopName) {
    return axios.post(`${API_BASE_URL}/booking/book-time`, {
        id: timeSlotId,
        contactInfo: contactInfo,
        workshopName: workshopName
    })

}

function getAvailableWorkshops() {
    return axios.get(`${API_BASE_URL}/booking/available-workshops`)
}

function getSupportedVehicleTypes() {
    return axios.get(`${API_BASE_URL}/booking/vehicle-types`)
}


export default {
    getAvailableTimes,
    bookTime,
    getAvailableWorkshops,
    getSupportedVehicleTypes
}
