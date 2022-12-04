import {EDataState} from "../enums/data-state.enum";

export interface DataState<T> {
  dataState: EDataState;
  appData?: T;
  error?: string;
}
