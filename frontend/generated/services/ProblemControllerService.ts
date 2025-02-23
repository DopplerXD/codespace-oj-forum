/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseJSONObject } from "../models/BaseResponseJSONObject";
import type { BaseResponseLong } from "../models/BaseResponseLong";
import type { BaseResponseProblemVO } from "../models/BaseResponseProblemVO";
import type { ProblemCreateDto } from "../models/ProblemCreateDto";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class ProblemControllerService {
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
      mediaType: "application/json"
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
    console.log("problemGet", pid);
    return __request(OpenAPI, {
      method: "GET",
      url: "/problem/{pid}",
      path: {
        "pid": pid
      }
    });
  }

  /**
   * @param current
   * @returns BaseResponseJSONObject OK
   * @throws ApiError
   */
  public static problemGetList(
    current: number
  ): CancelablePromise<BaseResponseJSONObject> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/problem/list",
      query: {
        "current": current
      }
    });
  }
}
