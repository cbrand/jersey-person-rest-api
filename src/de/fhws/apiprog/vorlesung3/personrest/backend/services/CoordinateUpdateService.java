package de.fhws.apiprog.vorlesung3.personrest.backend.services;

import de.fhws.apiprog.vorlesung3.personrest.objects.Coordinate;

public class CoordinateUpdateService implements UpdateService<Coordinate> {

	protected Coordinate coordinate;
	protected Coordinate data;
	
	/**
	 * @param coordinate Die Koordinate die aktuallisiert werden soll.
	 * @param data Die Daten die in der Koordinate gesetzt werden sollen.
	 */
	public CoordinateUpdateService(Coordinate coordinate, Coordinate data)
	{
		if(coordinate == null) {
			throw new IllegalArgumentException("The given coordinate may not be null");
		}
		if(data == null) {
			throw new IllegalArgumentException("The given data may not be null");
		}
		this.coordinate = coordinate;
		this.data = data;
	}
	
	public Coordinate update() {
		if(this.data.getLatitude() != null) {
			this.coordinate.setLatitude(this.data.getLatitude());
		}
		if(this.data.getLongitude() != null) {
			this.coordinate.setLongitude(this.data.getLongitude());
		}
		return this.coordinate;
	}
	
}
