export class Qualification {
    public degree!: string
    public institution!: string
    public certification!: string
    public yearObtained!: number
    public isVerified!: boolean
    public verificationDate!: Date
    public expiryDate!: Date
    public isActive!: boolean

    protected constructor() {}

    static create(): Qualification {
        return new Qualification()
    }

    static builder(): QualificationBuilder {
        return new QualificationBuilder()
    }
}

export class QualificationBuilder {
    private qualification: Qualification = Qualification.create()

    withDegree(degree: string): QualificationBuilder {
        this.qualification.degree = degree
        return this
    }

    withInstitution(institution: string): QualificationBuilder {
        this.qualification.institution = institution
        return this
    }

    withCertification(certification: string): QualificationBuilder {
        this.qualification.certification = certification
        return this
    }

    withYearObtained(yearObtained: number): QualificationBuilder {
        this.qualification.yearObtained = yearObtained
        return this
    }

    withIsVerified(isVerified: boolean): QualificationBuilder {
        this.qualification.isVerified = isVerified
        return this
    }

    withVerificationDate(verificationDate: Date): QualificationBuilder {
        this.qualification.verificationDate = verificationDate
        return this
    }

    withExpiryDate(expiryDate: Date): QualificationBuilder {
        this.qualification.expiryDate = expiryDate
        return this
    }

    withIsActive(isActive: boolean): QualificationBuilder {
        this.qualification.isActive = isActive
        return this
    }

    build(): Qualification {
        return this.qualification
    }
} 