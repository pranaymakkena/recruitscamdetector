export interface JobPosting {
  id?: number;
  title: string;
  company: string;
  location?: string;
  description: string;
  contactEmail?: string;
  sourceUrl?: string;
  submittedBy?: string;
  riskScore: number;
  riskLevel: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
  riskFactors?: string;
  aiAnalysis?: string;
  verified: boolean;
  flaggedAsFraud: boolean;
  submittedAt?: string;
  analyzedAt?: string;
}

export interface JobPostingRequest {
  title: string;
  company: string;
  location?: string;
  description: string;
  contactEmail?: string;
  sourceUrl?: string;
  submittedBy?: string;
}
