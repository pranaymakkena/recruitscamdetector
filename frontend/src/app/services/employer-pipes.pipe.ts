import { Pipe, PipeTransform } from '@angular/core';
import { Employer } from '../models/employer.model';

@Pipe({ name: 'verifiedCount', standalone: true })
export class VerifiedCountPipe implements PipeTransform {
  transform(employers: Employer[]): number {
    return employers.filter(e => e.verificationStatus === 'VERIFIED').length;
  }
}

@Pipe({ name: 'statusCount', standalone: true })
export class StatusCountPipe implements PipeTransform {
  transform(employers: Employer[], status: string): number {
    return employers.filter(e => e.verificationStatus === status).length;
  }
}
