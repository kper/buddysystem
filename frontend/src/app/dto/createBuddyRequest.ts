export class CreateBuddyRequest {
  constructor(private email: string, private courseId: number, private examDate: Date) {
  }
}
