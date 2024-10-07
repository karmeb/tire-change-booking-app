export default class BookingTimeDetails {
    constructor(id, workshopName, workshopAddress, vehicleTypes, time) {
        this.id = id;
        this.workshopName = workshopName;
        this.workshopAddress = workshopAddress;
        this.vehicleTypes = vehicleTypes

        const date = new Date(time);
        this.time = date.toLocaleString( 'en-GB', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: false,
        })
    }
}