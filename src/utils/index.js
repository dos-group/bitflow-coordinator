import moment from "moment";

export function createCurrentTimeFormatted() {
  moment().format();
  return moment().toISOString();
}

export function formatISODate(date) {
  if (date == null) { return "?" };
  try {
    const dateAndTime = date.split("T");
    const time = dateAndTime[1].split(".")[0];
    return dateAndTime[0] + " " + time;
  } catch(e) {
    return date;
  }
}
