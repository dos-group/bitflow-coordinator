import moment from "moment";

export default function createCurrentTimeFormatted() {
  moment().format();
  return moment().year() + "-" + Number(moment().month() + 1) + "-" + moment().date();
}