/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ProblemCreateDto = {
    problemId: string;
    title: string;
    author?: string;
    type?: number;
    timeLimit?: number;
    memoryLimit?: number;
    stackLimit?: number;
    description?: string;
    input?: string;
    output?: string;
    examples?: string;
    tags?: Array<string>;
    isRemote?: number;
    source?: string;
    difficulty?: number;
    hint?: string;
    auth?: number;
    ioScore?: number;
    codeShare?: number;
    judgeMode?: string;
    judgeCaseMode?: string;
    userExtraFile?: string;
    judgeExtraFile?: string;
    spjCode?: string;
    spjLanguage?: string;
    isRemoveEndBlank?: number;
    openCaseResult?: number;
    isUploadCase?: number;
};

