const getTimeDifference = (date1, date2, interval) => {
    var second = 1000,
        minute = second * 60,
        hour = minute * 60,
        day = hour * 24,
        week = day * 7;
    date1 = new Date(date1);
    date2 = new Date(date2);
    var timeSpan = date2 - date1;
    if (isNaN(timeSpan)) return NaN;
    switch (interval) {
        case "years":
            return date2.getFullYear() - date1.getFullYear();
        case "months":
            return (
                date2.getFullYear() * 12 +
                date2.getMonth() -
                (date1.getFullYear() * 12 + date1.getMonth())
            );
        case "weeks":
            return Math.floor(timeSpan / week);
        case "days":
            return Math.floor(timeSpan / day);
        case "hours":
            return Math.floor(timeSpan / hour);
        case "minutes":
            return Math.floor(timeSpan / minute);
        case "seconds":
            return Math.floor(timeSpan / second);
        default:
            return undefined;
    }
};

const getTimeString = (days, hours, minutes, seconds) => {
    if (days !== 0) {
        return days + " days ago";
    } else if (hours !== 0) {
        return hours + " hours ago";
    } else if (minutes !== 0) {
        return minutes + " minutes ago";
    } else {
        return seconds + " seconds ago";
    }
};

const getCurrentTime = () => {
    let date_ob = new Date();

    let date = ("0" + date_ob.getDate()).slice(-2);
    let month = ("0" + (date_ob.getMonth() + 1)).slice(-2);
    let year = date_ob.getFullYear();
    let hours = date_ob.getHours();
    let minutes = date_ob.getMinutes();
    let seconds = date_ob.getSeconds();

    // Same format as Backend
    return (
        year +
        "-" +
        month +
        "-" +
        date +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
    );
};

const TimeService = {
    timeDifference(postDate) {
        const currentTime = getCurrentTime();

        let days = getTimeDifference(postDate, currentTime, "days");
        let hours = getTimeDifference(postDate, currentTime, "hours");
        let minutes = getTimeDifference(postDate, currentTime, "minutes");
        let seconds = getTimeDifference(postDate, currentTime, "seconds");

        return getTimeString(days, hours, minutes, seconds);
    },
};

export default TimeService;
