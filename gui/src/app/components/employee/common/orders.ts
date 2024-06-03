import { Rooms } from "../../admin/common/rooms";
import { Foods } from "../../client/common/foods";

export class Orders {

    id: number | undefined;

    roomEntity: Rooms | undefined;

    foodModelSet: Foods[] | undefined;

    states: string | undefined;

}
