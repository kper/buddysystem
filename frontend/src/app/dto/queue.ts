import {Course} from './course';

export class Queue {
  constructor(public course: Course, public examDate: Date,
              public onCreate: Date) {
  }
}
