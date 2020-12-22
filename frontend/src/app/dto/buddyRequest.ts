import {Course} from './course';

export class BuddyRequest {
  constructor(private id: number, private email: string, private course: Course, private examDate: Date,
              private onCreate: Date, private confirmed: boolean) {
  }
}
