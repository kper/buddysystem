import {Globals} from '../global/globals';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Course} from '../dto/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private baseUri: string = this.globals.backendUri + '/course';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  public getAll(): Observable<Course[]> {
    return this.httpClient.get<Course[]>(this.baseUri);
  }
}
