export interface CommunityReport {
  id?: number;
  reporterName: string;
  reporterEmail?: string;
  college?: string;
  companyName: string;
  jobTitle?: string;
  description: string;
  scamType: 'PAYMENT_REQUEST' | 'FAKE_COMPANY' | 'IDENTITY_THEFT' | 'DATA_COLLECTION' | 'OTHER';
  status?: 'PENDING' | 'VERIFIED' | 'DISMISSED';
  upvotes?: number;
  reportedAt?: string;
}
