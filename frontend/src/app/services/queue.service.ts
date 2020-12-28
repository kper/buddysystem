import {Globals} from '../global/globals';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Queue} from '../dto/queue';

@Injectable({
  providedIn: 'root'
})
export class QueueService {
  private baseUri: string = this.globals.backendUri + '/queue';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  public getAll(): Observable<Queue[]> {
    return this.httpClient.get<Queue[]>(this.baseUri);
  }
}
