import {EStatus} from "../enums/status.enum";

export interface Server {
  id: number;
  ipAddress: string;
  serverName: string;
  memory: string,
  type: string,
  imgUrl: string,
  status: EStatus
}
