# 

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

## 生成接口调用代码

```shell
openapi --input http://localhost:8081/api/v3/api-docs --output ./generated --client axios
```

## OpenAPI.ts

```typescript
/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
// @ts-ignore
import type { ApiRequestOptions } from "./ApiRequestOptions";
import store from "@/store"; // 引入 Vuex 存储实例

type Resolver<T> = (options: ApiRequestOptions) => Promise<T>;
type Headers = Record<string, string>;

export type OpenAPIConfig = {
  BASE: string;
  VERSION: string;
  WITH_CREDENTIALS: boolean;
  CREDENTIALS: "include" | "omit" | "same-origin";
  TOKEN?: string | Resolver<string> | undefined;
  USERNAME?: string | Resolver<string> | undefined;
  PASSWORD?: string | Resolver<string> | undefined;
  HEADERS?: Headers | Resolver<Headers> | undefined;
  ENCODE_PATH?: ((path: string) => string) | undefined;
};

export const OpenAPI: OpenAPIConfig = {
  BASE: "http://localhost:8081",
  VERSION: "1.0.0",
  WITH_CREDENTIALS: true,
  CREDENTIALS: "include",
  TOKEN: undefined,
  USERNAME: undefined,
  PASSWORD: undefined,
  HEADERS: async () => {
    // 修改为异步函数
    const token = localStorage.getItem("token");
    const headers: Headers = {};
    if (token) {
      headers["Authorization"] = `Bearer ${token}`;
    }
    return headers;
  },
  ENCODE_PATH: undefined,
};

```
