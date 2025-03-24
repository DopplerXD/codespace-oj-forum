/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseBoolean } from "../models/BaseResponseBoolean";
import type { BaseResponseJSONObject } from "../models/BaseResponseJSONObject";
import type { BaseResponseJudgeVO } from "../models/BaseResponseJudgeVO";
import type { BaseResponseLong } from "../models/BaseResponseLong";
import type { BaseResponseProblemEditVO } from "../models/BaseResponseProblemEditVO";
import type { BaseResponseProblemVO } from "../models/BaseResponseProblemVO";
import type { JudgeSubmitDto } from "../models/JudgeSubmitDto";
import type { ProblemCreateDto } from "../models/ProblemCreateDto";
import type { ProblemDeleteDto } from "../models/ProblemDeleteDto";
import type { ProblemUpdateDto } from "../models/ProblemUpdateDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class ProblemControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseBoolean OK
   * @throws ApiError
   */
  public static problemUpdate(
    requestBody: ProblemUpdateDto
  ): CancelablePromise<BaseResponseBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/problem/update",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseLong OK
   * @throws ApiError
   */
  public static judgeSubmit(
    requestBody: JudgeSubmitDto
  ): CancelablePromise<BaseResponseLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/problem/submit/judge",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseBoolean OK
   * @throws ApiError
   */
  public static problemDelete(
    requestBody: ProblemDeleteDto
  ): CancelablePromise<BaseResponseBoolean> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/problem/delete",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseLong OK
   * @throws ApiError
   */
  public static problemCreate(
    requestBody: ProblemCreateDto
  ): CancelablePromise<BaseResponseLong> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/api/problem/create",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param pid
   * @returns BaseResponseProblemVO OK
   * @throws ApiError
   */
  public static problemGet(
    pid: string
  ): CancelablePromise<BaseResponseProblemVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/problem/get/summary/{pid}",
      path: {
        pid: pid,
      },
    });
  }

  /**
   * @param current
   * @param difficulty
   * @param tags
   * @param keyword
   * @returns BaseResponseJSONObject OK
   * @throws ApiError
   */
  public static problemGetList(
    current: number = 1,
    difficulty?: number,
    tags?: Array<string>,
    keyword?: string
  ): CancelablePromise<BaseResponseJSONObject> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/problem/get/problem/list",
      query: {
        current: current,
        difficulty: difficulty,
        tags: tags,
        keyword: keyword,
      },
    });
  }

  /**
   * @param judgeId
   * @returns BaseResponseJudgeVO OK
   * @throws ApiError
   */
  public static judgeGet(
    judgeId: number
  ): CancelablePromise<BaseResponseJudgeVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/problem/get/judge/{judgeId}",
      path: {
        judgeId: judgeId,
      },
    });
  }

  /**
   * @param current
   * @param problemId
   * @param username
   * @param status
   * @param language
   * @returns BaseResponseJSONObject OK
   * @throws ApiError
   */
  public static judgeGetList(
    current: number = 1,
    problemId?: number,
    username?: string,
    status?: number,
    language?: string
  ): CancelablePromise<BaseResponseJSONObject> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/problem/get/judge/list",
      query: {
        current: current,
        problemId: problemId,
        username: username,
        status: status,
        language: language,
      },
    });
  }

  /**
   * @param pid
   * @returns BaseResponseProblemEditVO OK
   * @throws ApiError
   */
  public static problemGetDetail(
    pid: string
  ): CancelablePromise<BaseResponseProblemEditVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/api/problem/get/detail/{pid}",
      path: {
        pid: pid,
      },
    });
  }
}
