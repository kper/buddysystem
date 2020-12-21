import {Globals} from '../global/globals';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Course} from '../dto/course';
import {CreateBuddyRequest} from '../dto/createBuddyRequest';
import {BuddyRequest} from '../dto/buddyRequest';

@Injectable({
  providedIn: 'root'
})
export class BuddyRequestService {
  private baseUri: string = this.globals.backendUri + '/buddyrequest';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  public create(req: CreateBuddyRequest): Observable<BuddyRequest> {
    return this.httpClient.post<BuddyRequest>(this.baseUri, req);
  }

  public confirm(id: number, token: string): Observable<any> {
    return this.httpClient.put<any>(this.baseUri + '/' + id + '?token=' + token, null);
  }

  public remove(id: any, token: any): Observable<any> {
    return this.httpClient.delete(this.baseUri + '/' + id + '?token=' + token, null);
  }
}
