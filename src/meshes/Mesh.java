package meshes;

public class Mesh {

    public enum CoffeeSize {
        SMALL(100), MEDIUM(200), LARGE(300);

        private int mlSize;

        CoffeeSize(int size) {
            this.mlSize = size;
        }

        public int getSize() {
            return mlSize;
        }
    }
    public Mesh(){
        CoffeeSize c = CoffeeSize.LARGE;
    }
}
