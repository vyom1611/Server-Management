import {Server} from "./server";

export interface EApiResponse {
  timeStamp: Date;
  statusCode: number;
  reason: string;
  message: string;
  developerMessage: string;
  data: { servers?: Server[], server?: Server }
}
