import {Component, OnInit} from '@angular/core';
import {AbstractControl, Form, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BuddyRequestService} from '../services/buddyrequest.service';
import {CourseService} from '../services/course.service';
import {CreateBuddyRequest} from '../dto/createBuddyRequest';
import {Observable, throwError} from 'rxjs';
import {Course} from '../dto/course';
import {catchError, tap} from 'rxjs/operators';
import {Router} from '@angular/router';

@Component({
  selector: 'app-buddyrequest-create',
  templateUrl: './buddyrequest-create.component.html',
  styleUrls: ['./buddyrequest-create.component.css']
})
export class BuddyrequestCreateComponent implements OnInit {
  form: FormGroup;
  error: boolean;
  errorMsg = '';
  courses: Observable<Course[]>;
  isLoading: boolean = false;

  constructor(private formBuilder: FormBuilder, private buddyService: BuddyRequestService, private courseService: CourseService,
              private router: Router) {
  }

  ngOnInit(): void {
    const nonWhiteSpaceRegExp: RegExp = new RegExp('\\S');
    this.form = this.formBuilder.group({
      email: ['', [Validators.email, Validators.required, Validators.minLength(3), Validators.pattern(nonWhiteSpaceRegExp)]],
      courseId: ['', [Validators.required, Validators.min(0), Validators.max(100000)]],
      examDate: ['', [Validators.required]]
    });

    this.courses = this.fetchAllCourses();
  }

  private fetchAllCourses(): Observable<Course[]> {
    return this.courseService.getAll().pipe(catchError(err => this.defaultErrorHandler(err)));
  }

  get email(): AbstractControl {
    return this.form.get('email');
  }

  get courseId(): AbstractControl {
    return this.form.get('courseId');
  }

  get examDate(): AbstractControl {
    return this.form.get('examDate');
  }

  public submit(data: CreateBuddyRequest): void {
    console.log('data', data);
    this.isLoading = true;

    this.buddyService.create(data).subscribe((value) => {
      console.log('ok');
      this.isLoading = false;
      this.router.navigate(['/success']);
    },
      (err) => this.defaultErrorHandler(err));
  }

  public vanishError(): void {
    this.error = false;
    this.errorMsg = '';
  }

  private defaultErrorHandler(error): Observable<any> {
    console.error(error);
    this.isLoading = false;
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
