import {
  OptionBase
} from "chakra-react-select";

interface LocationGroup extends OptionBase {
  label: string;
  value: string;
}

interface GenreGroup extends OptionBase {
  label: string;
  value: string;
}

interface RoleGroup extends OptionBase {
  label: string;
  value: string;
}

interface AvailableGroup extends OptionBase {
  label: string;
  value: boolean;
}

export type {  LocationGroup, GenreGroup, RoleGroup, AvailableGroup }