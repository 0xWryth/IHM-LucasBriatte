package fr.polytech.Tasks;

import fr.polytech.CanvaShape;

import java.util.ArrayList;

public class Clone implements Task {
    private ArrayList<Integer> addedIndex = new ArrayList<Integer>();
    ArrayList<CanvaShape> canvaObj;

    @Override
    public void execute(Object[] data) {
        // data[0] : type of task
        // data[1] : list of objects
        // data[2] : last added id

        int clonedId = 0;

        try {
            if (data.length == 0) {
                throw new Exception("You must provide some cloning data");
            }
            else if (data.length != 3){
                throw new Exception("Not the good number of params");
            }
            else if (!((data[0] instanceof String) && (data[1] instanceof ArrayList) && (data[2] instanceof Integer))) {
                throw new Exception("Cloning Tasks params are wrong");
            }
            else if (!data[0].equals("cloning")){
                throw new Exception("You can only provide a cloning task");
            }

            ArrayList<CanvaShape> futureCanvaObj = new ArrayList<CanvaShape>();
            canvaObj = (ArrayList<CanvaShape>) data[1];
            for (CanvaShape o : canvaObj) {
                if (o.isSelected()) {
                    CanvaShape cs = new CanvaShape(o, (Integer) data[2] + clonedId);
                    addedIndex.add(cs.getId());
                    futureCanvaObj.add(cs);
                    clonedId++;
                }
            }

            data[2] = (Integer) data[2] + clonedId;

            canvaObj.addAll(futureCanvaObj);
            data[1] = canvaObj;
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public ArrayList<CanvaShape> undo() {
        ArrayList<CanvaShape> futureCanvaObj = new ArrayList<>();
        for (CanvaShape canvaShape : canvaObj) {
            boolean flag = false;
            int index = 0;
            while (flag == false && index < addedIndex.size()) {
                if (canvaShape.getId() == addedIndex.get(index)) {
                    flag = true;
                }
                index++;
            }

            if (!flag) {
                futureCanvaObj.add(canvaShape);
            }
        }
        System.out.println("Undoing clone");
        return futureCanvaObj;
    }

    @Override
    public void redo() {

    }
}