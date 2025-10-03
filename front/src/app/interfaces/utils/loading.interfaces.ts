
export type Loading<T> = { item: T; loading: boolean; }
  | { loading: boolean };

export type LoadingWithErrors<T> = { item: T; loading: boolean; }
  | {loading: boolean}
  | {errors: Error[]; loading: boolean };
