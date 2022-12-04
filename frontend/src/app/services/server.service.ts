import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {EApiResponse} from "../interfaces/ApiResponse";
import {Server} from "../interfaces/server";
import {EStatus} from "../enums/status.enum";

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  private readonly apiURL: string = "http://localhost:8080";

  handleError(e: HttpErrorResponse) : Observable<never> {
    console.log(e);
    return throwError(`An error occurred: ${e.status}`);
  }

  constructor(private httpClient: HttpClient) {};

  servers$ = <Observable<EApiResponse>>
    this.httpClient.get<EApiResponse>(`${this.apiURL}/servers/list`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  ping$ = (ipAddress: string) => <Observable<EApiResponse>>
    this.httpClient.get<EApiResponse>(`${this.apiURL}/servers/ping/${ipAddress}`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  delete$ = (id: number) => <Observable<EApiResponse>>
    this.httpClient.delete<EApiResponse>(`${this.apiURL}/servers/delete/${id}`).pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  filter$ = (status: EStatus, response: EApiResponse) => <Observable<EApiResponse>>
    new Observable<EApiResponse>(
      subscriber => {
        console.log(response)
        subscriber.next(status === EStatus.ALL ? {...response, message: "Servers filtered by status"} : {
          // @ts-ignore
          ...response, message: response.data.servers.filter(server => server.status === status).length > 0 ?
            `Servers filtered by ${status === EStatus.SERVER_UP ? 'SERVER_UP' : 'SERVER_DOWN'} ` : "No servers with that status found",
          data : { servers: response.data.servers?.filter(server => server.status === status)}
        })
        subscriber.complete();
      }
    )
      .pipe(
      tap(console.log),
      catchError(this.handleError)
    );

  save$ = (server: Server) => <Observable<EApiResponse>>
    this.httpClient.post<EApiResponse>(`${this.apiURL}/servers/save`, server).pipe(
    tap(console.log),
    catchError(this.handleError)
  );



}
