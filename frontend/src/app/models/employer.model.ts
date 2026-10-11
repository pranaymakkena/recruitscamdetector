export interface Employer {
  id?: number;
  companyName: string;
  website?: string;
  industry?: string;
  linkedinUrl?: string;
  registrationNumber?: string;
  verificationStatus?: 'VERIFIED' | 'SUSPICIOUS' | 'BLACKLISTED' | 'UNKNOWN';
  trustScore?: number;
  verificationNotes?: string;
  reportCount?: number;
  lastChecked?: string;
}
