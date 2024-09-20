export interface Movie {
    _id?: string;
    producer?: string;
    duration: number;
    title: string;
    dateOfRelease?: Date;
    seriesPart?: boolean;
    isNotSaved?: boolean;
}