import {EDataState} from "../enums/data-state.enum";

export interface State<T> {
  dataState: EDataState;
  appData?: T;
  error?: string;
}
