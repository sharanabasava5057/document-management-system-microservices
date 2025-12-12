export interface DocumentMetadata {
  title: string;
  description: string;
  uploadedBy: string;
  fileName: string;
  uploadDate?: string; // optional - backend sets this if not provided
}
