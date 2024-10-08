function formatDateToString(date) {
    return date.toISOString().split('T')[0].replace(/-/g, '-');
}

function getFutureDate(daysFromToday) {
    let today = new Date();
    let futureDate = new Date()
    futureDate.setDate(today.getDate() + daysFromToday)
    return futureDate;
}


export default {
    formatDateToString,
    getFutureDate
}
