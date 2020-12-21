import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BuddyRequestService} from '../services/buddyrequest.service';
import {Observable, throwError} from 'rxjs';

@Component({
  selector: 'app-buddyrequest-clear',
  templateUrl: './buddyrequest-clear.component.html',
  styleUrls: ['./buddyrequest-clear.component.css']
})
export class BuddyrequestClearComponent implements OnInit {

  isLoading = true;
  success = false;
  error = false;
  errorMsg = '';

  constructor(private router: Router, private active: ActivatedRoute, private buddyService: BuddyRequestService) {
  }

  ngOnInit(): void {
    const id = this.active.snapshot.params['id'];
    this.active.queryParams.subscribe((params) => {
      const token = params.token;

      this.buddyService.remove(id, token).subscribe(() => {
          this.success = true;
          this.isLoading = false;
        },
        (err) => this.defaultErrorHandler(err));
    });
  }

  private defaultErrorHandler(error): Observable<any> {
    this.isLoading = false;
    console.error(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMsg = 'The backend seems not to be reachable';

    } else if (error.error.message) {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMsg = error.error.message;

    } else {
      this.errorMsg = error.error;
    }

    throw throwError(error);
  }

}
