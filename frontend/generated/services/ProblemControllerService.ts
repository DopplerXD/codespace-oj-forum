/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseBoolean } from "../models/BaseResponseBoolean";
import type { BaseResponseJSONObject } from "../models/BaseResponseJSONObject";
import type { BaseResponseLong } from "../models/BaseResponseLong";
import type { BaseResponseProblemEditVO } from "../models/BaseResponseProblemEditVO";
import type { BaseResponseProblemVO } from "../models/BaseResponseProblemVO";
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
      url: "/problem/update",
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
      url: "/problem/delete",
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
      url: "/problem/create",
      body: requestBody,
      mediaType: "application/json",
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
      url: "/problem/list",
      query: {
        current: current,
        difficulty: difficulty,
        tags: tags,
        keyword: keyword,
      },
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
      url: "/problem/get/summary/{pid}",
      path: {
        pid: pid,
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
      url: "/problem/get/detail/{pid}",
      path: {
        pid: pid,
      },
    });
  }
}
