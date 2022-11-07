import {EStatus} from "../enums/status.enum";

export interface Server {
  id: number;
  ipAddress: string;
  name: string;
  memory: string,
  type: string,
  imageUrl: string,
  status: EStatus
}
